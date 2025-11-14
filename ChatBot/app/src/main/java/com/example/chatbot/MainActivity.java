package com.example.chatbot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputEditText;
    private Button sendButton;
    private Button clearButton;

    private final List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;

    private SharedPreferences prefs;
    private final Gson gson = new Gson();

    private static final String PREFS = "chat_prefs";
    private static final String KEY_MESSAGES = "messages_json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        inputEditText = findViewById(R.id.inputEditText);
        sendButton = findViewById(R.id.sendButton);
        clearButton = findViewById(R.id.clearButton);

        adapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadMessages();

        sendButton.setOnClickListener(v -> sendMessage());
        clearButton.setOnClickListener(v -> clearAllMessages());
    }

    private void clearAllMessages() {
        messages.clear();
        adapter.notifyDataSetChanged();
        prefs.edit().remove(KEY_MESSAGES).apply(); // clear stored chat
    }

    /**
     * 1. Get the JSON string from prefs using KEY_MESSAGES (returns null if not found)
     * 2. If JSON exists:
     *    - Use Gson with TypeToken to convert JSON string to List<Message>
     *    - Clear the messages list and add all saved messages
     *    - Notify the adapter that data changed
     *    - Scroll to the last message if list is not empty
     */
    private void loadMessages() {
        String stringPrefs = prefs.getString(KEY_MESSAGES, null);
        messages.clear();
        messages.addAll(Objects.requireNonNull(gson.fromJson(stringPrefs, new TypeToken<List<Message>>() {
        }.getType())));
        adapter.notifyDataSetChanged();
        if (!messages.isEmpty()) {
            recyclerView.scrollToPosition(messages.size() - 1);
        }
    }

    /**
     * 1. Use Gson to convert messages list to JSON string
     * 2. Save the JSON string to SharedPreferences with KEY_MESSAGES
     */
    private void saveMessages() {
        String json = gson.toJson(messages);
        prefs.edit().putString(KEY_MESSAGES, json).apply();
    }

    /**
     * TODO: Handle send button click
     * 1. Get text from inputEditText and trim it
     * 2. Return early if text is empty
     * 3. Create new Message with text and isUser=true, add to messages list
     * 4. Notify adapter that item was inserted at the end
     * 5. Scroll RecyclerView to the last position
     * 6. Clear the inputEditText
     * 7. Call saveMessages()
     * 8. Call sendMessageToGemini() with the text
     */
    private void sendMessage() {

    }

    /**
     * TODO: Send message to Gemini API on background thread
     * 1. Get API key from BuildConfig.GEMINI_API_KEY
     * 2. Create a new Thread and start it
     * 3. Inside the thread:
     *    - Build the endpoint URL (see lab doc for format)
     *    - Escape special characters in userText (replace " with \" and \n with \\n)
     *    - Build JSON body: {"contents":[{"parts":[{"text":"escaped_text_here"}]}]}
     *    - Call doJsonPost() with endpoint and JSON body
     *    - Parse the response with parseGeminiResponse()
     *    - Display the AI message with displayAiMessage()
     *    - Wrap everything in try-catch and call displayAiMessage() with error on exception
     */
    private void sendMessageToGemini(String userText) {

    }

    /**
     * TODO: Make HTTP POST request
     * 1. Create URL object from endpoint string
     * 2. Open HttpURLConnection
     * 3. Set request method to "POST"
     * 4. Set request properties:
     *    - "Content-Type": "application/json; charset=UTF-8"
     *    - "Accept": "application/json"
     * 5. Set doOutput to true
     * 6. Write jsonBody to output stream (use getOutputStream())
     * 7. Check response code (getResponseCode())
     * 8. Read response from appropriate stream:
     *    - If code 200-299: use getInputStream()
     *    - Otherwise: use getErrorStream()
     * 9. Build response string from BufferedReader
     * 10. Don't forget to disconnect() in finally block
     * 11. Return the response string
     */
    private String doJsonPost(String endpoint, String jsonBody) throws Exception {

        return "";
    }

    /**
     * TODO: Parse Gemini API JSON response
     * Extract the text from: candidates[0].content.parts[0].text
     *
     * 1. Create JSONObject from json string
     * 2. Get "candidates" JSONArray
     * 3. Get first candidate (index 0) as JSONObject
     * 4. Get "content" JSONObject from candidate
     * 5. Get "parts" JSONArray from content
     * 6. Get first part (index 0) as JSONObject
     * 7. Get "text" string from part
     * 8. If any step fails or returns null/empty, check for "error" object
     * 9. Return the text or an error message
     */
    private String parseGeminiResponse(String json) {

        return "⚠️ Could not parse AI response.";
    }

    /**
     * Helper method to display AI message (PROVIDED - DO NOT MODIFY)
     * This uses runOnUiThread because it's called from a background thread
     */
    private void displayAiMessage(String text) {
        runOnUiThread(() -> {
            messages.add(new Message(text, false));
            adapter.notifyItemInserted(messages.size() - 1);
            recyclerView.scrollToPosition(messages.size() - 1);
            saveMessages();
        });
    }
}