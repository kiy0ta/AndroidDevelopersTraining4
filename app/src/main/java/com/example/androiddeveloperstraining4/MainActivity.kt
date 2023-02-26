package com.example.androiddeveloperstraining4

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        useThread()
//        useCoroutine()
//        useJob()
        rewrite()
    }

    private fun rewrite() {
        val states = arrayOf("Starting", "Doing Task 1", "Doing Task 2", "Ending")
        repeat(3) {
            Thread {
                println("${Thread.currentThread()} has started")
                for (i in states) {
                    println("${Thread.currentThread()} - $i")
                    Thread.sleep(50)
                }
            }.start()
        }
    }

    /**
     * コルーチンの主な特長のひとつは状態を保存できることです。
     * そのため、停止と再開ができます。コルーチンは実行される場合と実行されない場合があります。
     */
    private fun useCoroutine() {
        // デフォルトの Dispatcher を使用して、Global Scope 内に 3 つのコルーチンを作成しています。
        // アプリでコルーチンを使用するときは、他のスコープを使用します。
//        repeat(3) {
//            GlobalScope.launch {
//                println("Hi from ${Thread.currentThread()}")
//            }
//        }
    }

    /**
     * runBlockingは主に、main 関数とテストで、ブロックするコードとブロックしないコードを橋渡しするために使用されます。
     * 一般的な Android コードではあまり使用しません。
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun useJob() {
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val time: () -> String = { formatter.format(LocalDateTime.now()) }
        val textView: TextView = findViewById(R.id.text_view)
        runBlocking {
            val num1 = getValue(time)
            val num2 = getValue(time)
            textView.text = "result of num1 + num2 is ${num1 + num2}"
        }
    }

    suspend fun getValue(time: () -> String): Double {
        println("entering getValue() at ${time()}")
        delay(3000)
        println("leaving getValue() at ${time()}")
        return Math.random()
    }

    private fun useThread() {
        // スレッドを1つ作成して実行する
        val textView: TextView = findViewById(R.id.text_view)
        val thread = Thread {
            textView.text = "${Thread.currentThread()} has run."
        }
        thread.start()

        // スレッドを複数作成して実行する
        // コードで Thread を直接使用すると、さまざまな問題が発生する可能性があります。
        // スレッドの作成、切り替え、管理は、システム リソースを消費し、同時に管理できるスレッド数の制限に時間がかかります。
        // 作成のコストが大幅に増えることがあります。
        // アプリをスムーズに動作させるには、メインスレッドのパフォーマンスを高めることが重要です。
        // 複数のスレッドを操作するとき、競合状態という状態になることもあります。
        // これは、複数のスレッドがメモリ内の同じ値に同時にアクセスしようとした場合に発生します。
        // 競合状態は、再現の難しいランダムに見えるバグを引き起こす可能性があり、
        // アプリの頻繁かつ予期しないクラッシュの原因になることがあります。
        val states = arrayOf("Starting", "Doing Task 1", "Doing Task 2", "Ending")
        repeat(3) {
            Thread {
                println("${Thread.currentThread()} has started")
                for (i in states) {
                    println("${Thread.currentThread()} - $i")
                    Thread.sleep(50)
                }
            }.start()
        }
    }
}