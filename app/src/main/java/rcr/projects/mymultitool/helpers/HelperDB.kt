package rcr.projects.mymultitool.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import rcr.projects.mymultitool.model.Task


class HelperDB(
    context: Context?
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "multi.db"
        private val VERSAO_ATUAL = 1
    }
    val TABLE_NAME = "multi"
    val COLUMNS_ID = "id"
    val COLUMNS_TITLE = "title"
    val COLUMNS_DESCRIPTION = "description"
    val COLUMNS_DATE = "date"
    val COLUMNS_HOUR = "hour"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_TITLE TEXT NOT NULL," +
            "$COLUMNS_DESCRIPTION TEXT NOT NULL," +
            "$COLUMNS_DATE TEXT NOT NULL," +
            "$COLUMNS_HOUR TEXT NULL," +
            "" +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
             ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun buscarTarefas(busca: String) : List<Task> {
        //criarTarefa(Task("TRT", "14:00", "11/01/2022", "Vamos para uma nova tarefa incluída no banco de dados."))
        //criarTarefa2(Task("Exemplo", "10:20", "18/10/2020", "Inserido pelo método de inserção número 2"))
        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<Task>()
        val sql = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMNS_DATE"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        if(cursor == null) {
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            var task = Task(
                cursor.getString(cursor.getColumnIndex(COLUMNS_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_HOUR)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))
            )
            lista.add(task)
        }
        return lista
    }

    fun buscarDatas(busca: String) : List<Task> {

        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<Task>()
        val sql = "SELECT DISTINCT $COLUMNS_DATE FROM $TABLE_NAME ORDER BY $COLUMNS_DATE"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        if(cursor == null) {
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            var task = Task(
                "",
                "",
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                "cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION))",
                1
            )
            lista.add(task)
        }
        return lista
    }



    fun filtrarTarefas(busca: String) : List<Task> {
        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<Task>()
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMNS_TITLE LIKE '%$busca%'"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        if(cursor == null) {
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            var task = Task(
                cursor.getString(cursor.getColumnIndex(COLUMNS_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_HOUR)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))
            )
            lista.add(task)
        }
        return lista
    }

    fun filtrarPorData(busca: String) : List<Task> {
        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<Task>()
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMNS_DATE = '"+ busca +"'"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        if(cursor == null) {
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            var task = Task(
                cursor.getString(cursor.getColumnIndex(COLUMNS_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_HOUR)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))
            )
            lista.add(task)
        }
        return lista
    }



    fun filtrarTarefas2(busca: String) : List<Task> {
        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<Task>()
        val where = "$COLUMNS_TITLE LIKE ?"
        val buscaComPercentual = arrayOf("%$busca%")
        val cursor = db.query(TABLE_NAME, null, where, buscaComPercentual, null, null, null)

        if(cursor == null) {
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            var task = Task(
                cursor.getString(cursor.getColumnIndex(COLUMNS_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_HOUR)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))
            )
            lista.add(task)
        }
        return lista
    }



    fun selecionarTarefa(id: Int) : Task {
        var task = Task("Busca vazia", "00:01", "01/01/1979","Não existem tarefas com esse ID", 0)
        val db = readableDatabase ?: return task
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMNS_ID = $id"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return task

        if(cursor == null) {
            db.close()
            return Task("Busca vazia", "00:01", "01/01/1979","Não existem tarefas com esse ID", 0)
        }

        while (cursor.moveToNext()){
             task = Task(
                cursor.getString(cursor.getColumnIndex(COLUMNS_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_HOUR)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))
            )

        }
        return task
    }


    fun criarTarefa(task: Task){
        val db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUMNS_TITLE, $COLUMNS_HOUR, $COLUMNS_DATE, $COLUMNS_DESCRIPTION) VALUES (?, ?, ?, ?)"
        var array = arrayOf(task.title, task.hour, task.date, task.description)
        db.execSQL(sql, array)
        db.close()
    }

    fun criarTarefa2(task: Task){
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUMNS_TITLE, task.title)
        content.put(COLUMNS_HOUR, task.hour)
        content.put(COLUMNS_DATE, task.date)
        content.put(COLUMNS_DESCRIPTION, task.description)
        db.insert(TABLE_NAME, null, content)
        db.close()
    }

    fun excluirTarefa(task: Task){
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMNS_ID = ${task.id}"
        db.execSQL(sql)
        db.close()
    }

    fun atualizarTarefa(task: Task){
        val db = writableDatabase ?: return

        var content = ContentValues()
        content.put(COLUMNS_TITLE, task.title)
        content.put(COLUMNS_HOUR, task.hour)
        content.put(COLUMNS_DATE, task.date)
        content.put(COLUMNS_DESCRIPTION, task.description)

        val selection = "${COLUMNS_ID} = ?"
        val selectionArgs = arrayOf(task.id.toString())
        db.update(
            TABLE_NAME,
            content,
            selection,
            selectionArgs)


        //val sql = "UPDATE $TABLE_NAME SET title = ${task.title}, hour = ${task.hour}, date = ${task.date}, description = ${task.description} WHERE $COLUMNS_ID = ${task.id}"
        //db.update("multi", )
        db.close()
    }



}
