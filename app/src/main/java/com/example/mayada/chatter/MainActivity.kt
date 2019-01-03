package com.example.mayada.chatter

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayada.chatter.data.db.Message
import com.example.mayada.chatter.viewmodels.MessageViewModel
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var messageAdapter: MessageAdapter
    lateinit var firstUser: TextView
    lateinit var secondUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstUser = findViewById<TextView>(R.id.first_user_count)
        secondUser = findViewById<TextView>(R.id.second_user_count)
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        messageAdapter = messageViewModel.adapter
        val recyclerView = findViewById<RecyclerView>(R.id.messages_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter
        recyclerView.addItemDecoration(MarginItemDecoration(12))

        messageViewModel.getAllMessages().observe(this, Observer { message ->
           messageAdapter.setMessagesArray(message)
            messageViewModel.HeaderInfo(firstUser, secondUser)
        })
        val button = findViewById<Button>(R.id.buttonOK)
        val editMessage = findViewById<EditText>(R.id.edit_message)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        button.setOnClickListener {
            val currentText = editMessage.text.toString()
            val currentUser = when (radioGroup.checkedRadioButtonId) {
                R.id.user1Select -> 1
                R.id.user2Select -> 2
                else -> throw IllegalArgumentException()
            }
            messageViewModel.InsertMessage(Message(currentUser, currentText))
            messageViewModel.HeaderInfo(firstUser, secondUser)
            editMessage.text.clear()
        }

        messageAdapter.setOnItemClickListener(object : MessageAdapter.OnItemClickListener {
            override fun onItemClick(message: Message, view: View) {
                val editText = findViewById<EditText>(R.id.edit_current_text)
                messageViewModel.HeaderInfo(firstUser, secondUser)
                messageViewModel!!.showPopup(view, message, this@MainActivity, editText)
            }
        })
    }
}