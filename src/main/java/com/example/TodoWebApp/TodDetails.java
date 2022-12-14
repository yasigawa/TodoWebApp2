package com.example.TodoWebApp;

import java.io.Serializable;

public interface TodDetails extends Serializable {
    String getId();      // ユーザーIDを返す
    String getUsername();      // ユーザーIDを返す
    String getTitle();  // TODOのタイトルを返す
    String getDetails();  // TODOの詳細を返す
    String getTododate();  // TODOの日時を返す
    String getTodotime();  // TODOの日時を返す
    String getDone();  // ステータスを返す
}
