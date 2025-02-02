package com.communisol.clappybee.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

data class Game(
    val screenWidth: Int = 0,
    val screenHeight: Int = 0,
    val gravity: Float = 0.3f,
    val beeRadius: Float = 30f,
    val beeJumpImpulse: Float = -8f,
    val beeMaxVelocity: Float = 25f,
    val pipeWidth: Float = 150f,
    val pipeVelocity: Float = 2f,
    val pipeGapSize: Float = 350f
) {
    var status by mutableStateOf(GameStatus.Idle)
        private set
    var beeVelocity by mutableStateOf(0f)
        private set
    var bee by mutableStateOf(
        Bee(
            x = (screenWidth / 4).toFloat(), y = (screenHeight / 2).toFloat(), radius = beeRadius
        )
    )
        private set

    var pipePairs = mutableStateListOf<PipePair>()

    fun start() {
        status = GameStatus.Started
    }

    fun gameOver() {
        status = GameStatus.Over
    }

    fun jump() {
        beeVelocity = beeJumpImpulse
    }

    fun restart() {
        resetBeePosition()
        removePipes()
        start()
    }

    private fun removePipes() {
        pipePairs.clear()
    }

    private fun resetBeePosition() {
        bee = bee.copy(
            y = (screenHeight / 2).toFloat()
        )
        beeVelocity = 0f
    }

    fun updateGameProgress() {
        pipePairs.forEach {
            if (isCollision(it)) {
                gameOver()
                return
            }
        }
        if (bee.y < 0) {
            stopTheBee()
            return
        } else if (bee.y > screenHeight) {
            gameOver()
            return
        }

        beeVelocity = (beeVelocity + gravity).coerceIn(-beeMaxVelocity, beeMaxVelocity)
        bee = bee.copy(y = bee.y + beeVelocity)

        spawnPipes()
    }

    private fun spawnPipes() {
        pipePairs.forEach {
            it.x -= pipeVelocity
        }
        pipePairs.removeAll { it.x + pipeWidth < 0 }

        if (pipePairs.isEmpty() || pipePairs.last().x < screenWidth / 2) {
            val initialPipeX = screenWidth.toFloat() + pipeWidth
            val topHeight = Random.nextFloat() * (screenHeight / 2)
            val bottomHeight = screenHeight - topHeight - pipeGapSize
            val newPipePair = PipePair(
                x = initialPipeX,
                y = topHeight + pipeGapSize / 2,
                topHeight = topHeight,
                bottomHeight = bottomHeight
            )
            pipePairs.add(newPipePair)
        }
    }

    private fun isCollision(pipePair: PipePair): Boolean {
        // Check horizontal collision. Bee overlaps the Pipe's X range.
        val beeRightEdge = bee.x + bee.radius
        val beeLeftEdge = bee.x - bee.radius
        val pipeLeftEdge = pipePair.x - pipeWidth / 2
        val pipeRightEdge = pipePair.x + pipeWidth / 2
        val horizontalCollision = beeRightEdge > pipeLeftEdge
                && beeLeftEdge < pipeRightEdge

        // Check if bee is within the vertical gap.
        val beeTopEdge = bee.y - bee.radius
        val beeBottomEdge = bee.y + bee.radius
        val gapTopEdge = pipePair.y - pipeGapSize / 2
        val gapBottomEdge = pipePair.y + pipeGapSize / 2
        val beeInGap = beeTopEdge > gapTopEdge
                && beeBottomEdge < gapBottomEdge

        return horizontalCollision && !beeInGap
    }

    fun stopTheBee() {
        beeVelocity = 0f
        bee = bee.copy(y = 0f)
    }
}