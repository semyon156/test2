package com.example.figma

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.figma.domain.model.QueueModel
import com.example.figma.domain.queue.MyQueue
import com.example.figma.presentation.ui.screen.OnboardingScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.LinkedList
import java.util.Queue

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun test3() {
        val myQueue = MyQueue(LinkedList())
        myQueue.addItem(QueueModel(R.drawable.onboard_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        myQueue.addItem(QueueModel(R.drawable.onboard_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        myQueue.addItem(QueueModel(R.drawable.onboard_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))

        rule.setContent {
            OnboardingScreen(
                image = myQueue.peekItem()!!.image,
                text1 = myQueue.peekItem()!!.text1,
                text2 = myQueue.peekItem()!!.text2,
                clickNext = { myQueue.pollItem() },
                clickSkip = { myQueue.clearQueue() },
                clickSignIn = {  },
                clickSignUp = {  },
                queueSize = myQueue.getSize()
            )
        }

        rule.onNodeWithTag("next").assertTextEquals("Next")
    }

    @Test
    fun test4() {
        val myQueue = MyQueue(LinkedList())

        rule.setContent {
            OnboardingScreen(
                image = myQueue.peekItem()!!.image,
                text1 = myQueue.peekItem()!!.text1,
                text2 = myQueue.peekItem()!!.text2,
                clickNext = { myQueue.pollItem() },
                clickSkip = { myQueue.clearQueue() },
                clickSignIn = {  },
                clickSignUp = {  },
                queueSize = myQueue.getSize()
            )
        }

        rule.onNodeWithTag("next").assertTextEquals("Sign Up")
    }

    @Test
    fun test5() {
        val myQueue = MyQueue(LinkedList())

        rule.setContent {
            OnboardingScreen(
                image = myQueue.peekItem()!!.image,
                text1 = myQueue.peekItem()!!.text1,
                text2 = myQueue.peekItem()!!.text2,
                clickNext = { myQueue.pollItem() },
                clickSkip = { myQueue.clearQueue() },
                clickSignIn = {  },
                clickSignUp = {  },
                queueSize = myQueue.getSize()
            )
        }

        rule.onNodeWithText("Sign in").performClick()
        rule.onNodeWithTag("holder").assertIsDisplayed()
    }
}