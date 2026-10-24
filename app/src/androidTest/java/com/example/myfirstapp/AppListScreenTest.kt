package com.example.myfirstapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Rect
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import android.view.InputDevice
import android.view.MotionEvent
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class AppListScreenTest {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val automation = instrumentation.uiAutomation

    @Test
    fun rotationKeepsAppsAndDoesNotRepeatSnackbar() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            waitFor { hasText("Камера") }
            lateinit var originalViewModel: AppListViewModel
            scenario.onActivity {
                originalViewModel = ViewModelProvider(it)[AppListViewModel::class.java]
            }
            tapToolbar()
            waitFor { hasText("Вы нажали на панель") }

            scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            waitFor {
                instrumentation.targetContext.resources.configuration.orientation ==
                    Configuration.ORIENTATION_LANDSCAPE && hasText("Камера")
            }
            instrumentation.waitForIdleSync()
            scenario.onActivity {
                val viewModel = ViewModelProvider(it)[AppListViewModel::class.java]
                assertSame(originalViewModel, viewModel)
                assertSame(originalViewModel.apps, viewModel.apps)
            }
            assertTrue(!hasText("Вы нажали на панель"))
            tapToolbar()
            waitFor { hasText("Вы нажали на панель") }
        }
    }

    private fun findText(node: AccessibilityNodeInfo?, text: String): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.text?.toString() == text) return node
        for (index in 0 until node.childCount) {
            findText(node.getChild(index), text)?.let { return it }
        }
        return null
    }

    private fun hasText(text: String): Boolean =
        findText(automation.rootInActiveWindow, text) != null

    private fun tapToolbar() {
        val node = requireNotNull(findText(automation.rootInActiveWindow, "Список приложений"))
        val bounds = Rect()
        node.getBoundsInScreen(bounds)
        val time = SystemClock.uptimeMillis()
        for (action in listOf(MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP)) {
            val event = MotionEvent.obtain(time, SystemClock.uptimeMillis(), action,
                bounds.centerX().toFloat(), bounds.centerY().toFloat(), 0)
            event.source = InputDevice.SOURCE_TOUCHSCREEN
            automation.injectInputEvent(event, true)
            event.recycle()
        }
    }

    private fun waitFor(condition: () -> Boolean) {
        val deadline = SystemClock.uptimeMillis() + 10_000
        while (!condition() && SystemClock.uptimeMillis() < deadline) {
            SystemClock.sleep(100)
        }
        assertTrue("Ожидаемое состояние экрана не появилось", condition())
    }
}
