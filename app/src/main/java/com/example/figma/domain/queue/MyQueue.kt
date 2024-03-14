package com.example.figma.domain.queue

import com.example.figma.R
import com.example.figma.domain.model.QueueModel
import java.util.Queue

class MyQueue(private val queue: Queue<QueueModel>) {
    fun addItem(item: QueueModel) = queue.add(item)

    fun pollItem(): QueueModel? = if (queue.isNotEmpty()) queue.poll() else QueueModel(
        R.drawable.onboard_3,
        "Real-time Tracking",
        "Track your packages/items from the comfort of your home till final destination"
    )

    fun peekItem(): QueueModel? = if (queue.isNotEmpty()) queue.peek() else QueueModel(
        R.drawable.onboard_3,
        "Real-time Tracking",
        "Track your packages/items from the comfort of your home till final destination"
    )

    fun getSize(): Int = queue.size

    fun clearQueue() = queue.clear()
}