package com.example.figma

import com.example.figma.domain.model.QueueModel
import com.example.figma.domain.queue.MyQueue
import org.junit.Test

import org.junit.Assert.*
import java.util.LinkedList
import java.util.Queue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test1() {
        val myQueue = MyQueue(LinkedList())
        myQueue.addItem(QueueModel(R.drawable.onboard_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        myQueue.addItem(QueueModel(R.drawable.onboard_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        myQueue.addItem(QueueModel(R.drawable.onboard_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))

        val testQueue: Queue<QueueModel> = LinkedList()
        val newTestQueue: Queue<QueueModel> = LinkedList()

        newTestQueue.add(QueueModel(R.drawable.onboard_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        newTestQueue.add(QueueModel(R.drawable.onboard_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        newTestQueue.add(QueueModel(R.drawable.onboard_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))

        testQueue.add(myQueue.pollItem())
        testQueue.add(myQueue.pollItem())
        testQueue.add(myQueue.pollItem())

        assertEquals(testQueue, newTestQueue)
    }

    @Test
    fun test2() {
        val myQueue = MyQueue(LinkedList())
        myQueue.addItem(QueueModel(R.drawable.onboard_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        myQueue.addItem(QueueModel(R.drawable.onboard_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        myQueue.addItem(QueueModel(R.drawable.onboard_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))

        val testQueue: Queue<QueueModel> = LinkedList()

        val oneElementQueue = 1

        assertEquals(myQueue.getSize(), 3)

        testQueue.add(myQueue.pollItem())

        assertEquals(myQueue.getSize(), 3 - oneElementQueue)
    }
}