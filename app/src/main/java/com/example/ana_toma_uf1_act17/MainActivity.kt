package com.example.ana_toma_uf1_act17


import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameBoard: GridLayout
    private lateinit var statusText: TextView
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private var currentPlayer = "X"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameBoard = findViewById(R.id.gameBoard)
        statusText = findViewById(R.id.statusText)

        setupBoard()
    }

    private fun setupBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val button = Button(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 200
                        height = 200
                        setMargins(8, 8, 8, 8)
                    }
                    textSize = 24f
                    setOnClickListener { onButtonClick(this, i, j) }
                }
                gameBoard.addView(button)
            }
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (!gameActive || board[row][col] != null) return

        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWin()) {
            statusText.text = "Player $currentPlayer wins!"
            gameActive = false
        } else if (isBoardFull()) {
            statusText.text = "It's a draw!"
            gameActive = false
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusText.text = "Turn: $currentPlayer"
        }
    }

    private fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0 until 3) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true
        }

        // Check diagonals
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }
}