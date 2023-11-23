package com.example.cbmoney;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ExpenseEntity.class}, version = 1)
public abstract class ExpenseDatabase extends RoomDatabase{
    private static ExpenseDatabase instance; // 單例模式確保一個類別僅有一個實例，並提供一個全域訪問點。
    public abstract ExpenseDao expenseDao();// 這個方法允許你獲取 ExpenseDao 實例，從而可以使用其中定義的方法來執行數據庫操作，比如插入、查詢、更新、刪除等。


    // 這個方法確保在多線程環境中只有一個數據庫實例。這是使用 synchronized 保證同一時間只有一個線程能夠進入 getInstance 方法
    // 使用 Room 框架的 databaseBuilder 方法創建一個新的 ExpenseDatabase 實例。
    // context.getApplicationContext() 確保取得的是應用程式的全域上下文，以避免可能的內存洩漏。
    // .fallbackToDestructiveMigration()，如果版本號發生改變，這個方法將使用破壞性遷移，即刪除舊有的資料表，重新創建新的資料表。
    // .build()，建立 NoteDatabase 實例
    public static synchronized ExpenseDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ExpenseDatabase.class, "expense_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }




    // 這段程式碼是使用 Room 框架時，可以提供的一個回調（Callback）類別，用於處理數據庫的創建事件
    // onCreate 方法是在數據庫首次創建時被調用的。在這個方法中執行一些初始化數據庫的操作，例如插入初始數據
    // SupportSQLiteDatabase db 是 Room 框架提供的一個對象，表示創建時的 SQLite 數據庫
    // 在使用 AsyncTask 的時候，execute() 是啟動該 AsyncTask 執行的方法。
    // doInBackground 是 AsyncTask 中實際執行在後台線程上的方法，但我們不直接呼叫它。
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
            // 在數據庫創建時執行的操作
        }
    };


    // 這段程式碼是使用 AsyncTask 在後台線程中執行的一個任務，目的是將一條新的 ExpenseDao 插入到 Room 數據庫中
    private static class PopulatedbAsyncTask extends AsyncTask<Void,Void,Void> {
        private ExpenseDao expenseDao;
        private PopulatedbAsyncTask(ExpenseDatabase db){

            expenseDao = db.expenseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.insert(new ExpenseEntity("食物","麥當勞",150,"現金",2023,10,15));
            expenseDao.insert(new ExpenseEntity("食物","肯德基",89,"現金",2023,10,24));
            expenseDao.insert(new ExpenseEntity("生活用品","洗髮精",300,"現金",2023,11,15));
            return null;
        }
    }
}
