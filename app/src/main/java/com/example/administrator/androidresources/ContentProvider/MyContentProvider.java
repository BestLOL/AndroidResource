package com.example.administrator.androidresources.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.administrator.androidresources.Sqlite.MyDatabaseHelper;

//内容提供者
public class MyContentProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.databasetest.provider";
    private static UriMatcher uriMatcher;

    private MyDatabaseHelper dbHelper;//内容提供器数据库支持

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    /**
     * 初始化内容提供器的时候调用。通常会在这里完成对数据库的创建和升级等操作。
     * 返回true表示内容提供器初始化成功，返回false则表示失败。
     * 注意，只有当存在ContentResolver尝试访问我们的程序中的数据时，内容提供器才会被初始化。
     */
    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 1);//创建内容提供器要使用的数据库
        return true;//这里一定返回true，不然内容提供器无法被使用
    }

    /**
     * 从内容提供器中查询数据。
     * 使用uri参数来确定查询的哪张表，
     * projection参数用于确定查询的哪一列，
     * selection和selectionArgs参数用于约束查询哪些行，
     * sortOrder参数用于对结果进行排序，查询的结果存放在Cursor对象中返回。
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id = ?", new String[]
                        { bookId }, null, null, sortOrder);
                break;

            case CATEGORY_DIR:
                cursor = db.query("Category", projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id = ?", new String[]
                        { categoryId }, null, null, sortOrder);
                break;

            default:
                break;
        }
        return cursor;
    }

    /**
     * 根据传入的内容URI来返回相应的MIME类型。
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book";

            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book";

            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category";

            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.category";

        }
        return null;
    }

    /**
     * 向内容提供器中添加一条数据。
     * 使用uri参数来确定要添加的表，待添加的数据保存在values参数中。
     * 添加完成后，返回一个用于表示这条新纪录的URI
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:

            case BOOK_ITEM:
                long newBookId = db.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" +
                        newBookId);
                break;

            case CATEGORY_DIR:

            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" +
                        newCategoryId);
                break;

            default:
                break;
        }

        return uriReturn;
    }

    /**
     *从内容提供器中删除数据。
     * 使用uri参数来确定删除哪一张表中的数据，
     * selection和selectionArgs参数用于约束删除哪些行，被删除的行数将作为返回值返回。
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 删除数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {

            case BOOK_DIR:
                deletedRows = db.delete("Book", selection, selectionArgs);
                break;

            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Book", "id = ?", new String[] { bookId });
                break;

            case CATEGORY_DIR:
                deletedRows = db.delete("Category", selection, selectionArgs);
                break;

            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Category", "id = ?", new String[]
                        { categoryId });
                break;

            default:
                break;
        }
        return deletedRows;
    }

    /**
     * 更新内容提供器中已有的数据。
     * 使用uri参数来确定更新哪一张表中的数据，新数据保存着values参数当中，
     * selection和selectionArgs参数用于约束更新哪些行，受影响的行数将作为返回值返回。
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // 更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = db.update("Book", values, selection, selectionArgs);
                break;

            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book", values, "id = ?", new String[]
                        { bookId });
                break;

            case CATEGORY_DIR:
                updatedRows = db.update("Category", values, selection,
                        selectionArgs);
                break;

            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category", values, "id = ?", new String[]
                        { categoryId });
                break;

            default:
                break;
        }

        return updatedRows;
    }
}
