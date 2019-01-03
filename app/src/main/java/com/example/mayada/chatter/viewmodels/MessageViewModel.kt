package com.example.mayada.chatter.viewmodels

import android.app.Application
import android.content.Context
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mayada.chatter.MessageAdapter
import com.example.mayada.chatter.R
import com.example.mayada.chatter.data.MessageRepository
import com.example.mayada.chatter.data.db.Message


class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: MessageRepository = MessageRepository(application)

    internal val allMessages: LiveData<List<Message>>

    init {
        allMessages = mRepository.allMessages
    }

    val adapter = MessageAdapter()
    fun InsertMessage(message: Message) {
        mRepository.insert(message)
    }

    fun UpdateMessage(view: View, message: Message, context: Context, editText: EditText) {
        val messageText: TextView = view.findViewById(R.id.message_text)
        var text: String = messageText.text.toString()
        editText.setText(text)

        messageText.visibility = View.GONE
        editText.visibility = View.VISIBLE
        editText.requestFocus()
        showKeyboard(editText, context)
        editText.imeOptions = EditorInfo.IME_ACTION_DONE
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setOnEditorActionListener { view, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    messageText.text = editText.text
                    editText.visibility = View.GONE
                    messageText.visibility = View.VISIBLE
                    text = messageText.text.toString()
                    mRepository.update(Message(message.messageUser, text))
                    editText.setText("")
                    true
                }
                else -> false
            }
        }
    }

    fun DeleteMessage(message: Message) {
        mRepository.delete(message)
    }

    fun getUserMessageCount(userId: Int): Int {
        return getAllMessages().value!!.filter { it.messageUser == userId }.count()
    }

    fun getAllMessages(): LiveData<List<Message>> {
        return mRepository.getAllMessages()
    }

    fun showPopup(view: View, message: Message, context: Context, editText: EditText) {
        var popup: PopupMenu? = null;
        popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.message_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_edit -> {
                    UpdateMessage(view, message, context, editText)
                    popup.setOnMenuItemClickListener { true }
                }
                R.id.menu_delete -> {
                    DeleteMessage(message)
                }
                R.id.menu_cancel -> {
                    popup.setOnMenuItemClickListener { false }
                }
            }

            true
        })

        popup.show()
    }

    private fun showKeyboard(editText: EditText, currentContext: Context) {
        editText.postDelayed({
            val keyboard = currentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(editText, 0)
        }, 100)
    }

    fun HeaderInfo(firstUser: TextView, secondUser: TextView)
    {
        var user1AmountOfMessages: Int = 0
        var user2AmountOfMessages: Int = 0
        user1AmountOfMessages = getUserMessageCount(1)
        user2AmountOfMessages = getUserMessageCount(2)
        var user1Display: String = "User 1: " + user1AmountOfMessages.toString()
        var user2Display: String = "User 2: " + user2AmountOfMessages.toString()
        firstUser.text = user1Display
        secondUser.text = user2Display
    }
}