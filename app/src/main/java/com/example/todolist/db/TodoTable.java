package com.example.todolist.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.Todo;

import java.util.ArrayList;

public class TodoTable {

        public static final String TABLE_NAME = "todos";
        interface columns {
            String ID = "id";
            String TASK = "task";
            String DONE = "done";
        }
        public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+
                "\n( " +columns.ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +columns.TASK+ " TEXT, "
                +columns.DONE+ " BOOLEAN );";
        public void insertTODO(SQLiteDatabase db, Todo todo){
            ContentValues row = new ContentValues();
            row.put(columns.TASK,todo.getTask());
            row.put(columns.DONE,todo.isDone());
            db.insert(TABLE_NAME,null,row);
        }
        public void deleteTODO(SQLiteDatabase db, Todo todo){
            db.delete(TABLE_NAME,columns.ID+ " = ( SELECT "+columns.ID+ " FROM "+TABLE_NAME+ " WHERE "+columns.TASK+" = \""+todo.toString()+"\" LIMIT 1)",null);
        }
        public ArrayList<Todo> getAllTodos(SQLiteDatabase db){
            ArrayList todos = new ArrayList<Todo>();
            Cursor cursor = db.query(TABLE_NAME,
                    new String[]{columns.ID,columns.TASK,columns.DONE},
                    null,null,null,
                    null,null);
            while (cursor.moveToNext()){
                Todo todo = new Todo(cursor.getString(1),cursor.getInt(2)==1);
                todos.add(todo);
            }
            return todos;
        }


}
