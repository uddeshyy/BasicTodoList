package com.example.todolist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.db.MyDBHelper;
import com.example.todolist.db.TodoTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList todos = new ArrayList<Todo>();
    MyDBHelper myDBHelper = new MyDBHelper(this);
    TodoTable todoTable = new TodoTable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText etTodo = findViewById(R.id.etTodo);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnDelete = findViewById(R.id.btnDel);
        ListView lvItems = findViewById(R.id.lvItems);


        TodoAdapter todoAdapter = new TodoAdapter();
        lvItems.setAdapter(todoAdapter);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        todos.clear();
        todos.addAll(todoTable.getAllTodos(db));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etTodo.getText().toString().isEmpty()){
                    Todo newTodo = new Todo(etTodo.getText().toString(),false);
                    todoTable.insertTODO(db,newTodo);
                    todos.clear();
                    todos.addAll(todoTable.getAllTodos(db));
                    todoAdapter.notifyDataSetChanged();
                    etTodo.setText("");
                }
            }
        });
    }
    class TodoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return todos.size();
        }

        @Override
        public Todo getItem(int position) {
            return (Todo) todos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.todo_items,parent,false);
            TextView text1 = view.findViewById(R.id.text1);
            Button btnDelete = view.findViewById(R.id.btnDel);
            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            text1.setText(todos.get(position).toString());
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoTable.deleteTODO(db,(Todo) todos.get(position));
                    todos.clear();
                    todos.addAll(todoTable.getAllTodos(db));
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }

}