package com.example.cbmoney;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<Integer> totalExpenseForMonth;
    private LiveData<Integer> totalExpenseForDay;
    private LiveData<Integer> categoryExpenseForMonth;
    private LiveData<List<ExpenseEntity>> allExpensesForMonth;
    private LiveData<List<ExpenseEntity>> allExpensesForDay;

    public ExpenseRepository(Application application) {
        ExpenseDatabase expenseDatabase = ExpenseDatabase.getInstance(application);
        expenseDao = expenseDatabase.expenseDao();

    }

    public void setTotalExpenseForMonth(int year, int month) {
        totalExpenseForMonth = expenseDao.getTotalExpenseForMonth(year, month);
    }

    public void setTotalExpenseForDay(int year, int month,int day) {
        totalExpenseForDay = expenseDao.getTotalExpenseForDay(year, month,day);
    }

    public void setCategoryExpenseForMonth(int year, int month, String category) {
        categoryExpenseForMonth = expenseDao.getCategoryExpenseForMonth(year, month, category);
    }

    public void setAllExpensesForMonth(int year, int month) {
        allExpensesForMonth = expenseDao.getAllExpensesForMonth(year, month);
    }

    public void setAllExpensesForDay(int year, int month,int day) {
        allExpensesForDay = expenseDao.getAllExpensesForDay(year, month,day);
    }

    public LiveData<Integer> getTotalExpenseForMonth() {
        return totalExpenseForMonth;
    }
    public LiveData<Integer> getTotalExpenseForDay() {
        return totalExpenseForDay;
    }

    public LiveData<Integer> getCategoryExpenseForMonth() {
        return categoryExpenseForMonth;
    }

    public LiveData<List<ExpenseEntity>> getAllExpensesForMonth() {
        return allExpensesForMonth;
    }

    public LiveData<List<ExpenseEntity>> getAllExpensesForDay() {
        return allExpensesForDay;
    }
    // 這種方法的好處是，它將插入操作放在後台線程上執行，以避免阻塞主線程

    public void insert(ExpenseEntity expense) {
        new InsertExpenseAsyncTask(expenseDao).execute(expense); // /創建 InsertExpenseAsyncTask 實例時，將 ExpenseDao 作為參數傳遞給它的構造函數
    }
    public void update(ExpenseEntity expense) {

        new UpdateExpenseAsyncTask(expenseDao).execute(expense);// 調用 execute 方法，該方法將啟動一個新的後台線程並執行 doInBackground 方法。
    }
    public void delete(ExpenseEntity expense) {
        new DeleteNoteAsyncTask(expenseDao).execute(expense);
    }



    // Note：表示 doInBackground 方法的參數類型/Void：表示進度（progress）更新的類型/Void：表示 doInBackground 方法的返回類型
    //這裡的ExpenseDao 不需要實體化，因為它是在 InsertExpenseAsyncTask 這個靜態內部類中被使用，
    // 而 InsertExpenseAsyncTask 的實例是由外部類 ExpenseRepository 創建和實體化的。
    //創建 InsertExpenseAsyncTask 實例時，將 ExpenseDao 作為參數傳遞給它的構造函數（上方）
    private static class InsertExpenseAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private ExpenseDao expenseDao;

        public InsertExpenseAsyncTask(ExpenseDao expenseDao) {

            this.expenseDao = expenseDao;
        }
        // 這是 AsyncTask 的一個方法，它執行在後台線程上，用於執行任務
        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {
            // 在這裡執行插入花費的操作
            expenseDao.insert(expenses[0]); // doInBackground 方法使用了可變參數，
                                            // 所以可以傳遞多個 ExpenseEntity 對象，
                                            // 但在這個專案中，只傳遞了一個對象，expenses[0]就是那個對象了
            return null;
        }
    }
    private static class UpdateExpenseAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private ExpenseDao expenseDao;

        public UpdateExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        // 這是 AsyncTask 的一個方法，它執行在後台線程上，用於執行任務
        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {
            // 在這裡執行更新筆記的操作
            expenseDao.update(expenses[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private ExpenseDao expenseDao;

        public  DeleteNoteAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        // 這是 AsyncTask 的一個方法，它執行在後台線程上，用於執行任務
        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {
            // 在這裡執行刪除筆記的操作
            expenseDao.delete(expenses[0]);
            return null;
        }
    }

}
