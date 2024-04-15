package com.example.mobile_9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим TextInputEditText для имени файла и его содержимого
        TextInputEditText fileName = findViewById(R.id.fileName);
        TextInputEditText fileContent = findViewById(R.id.fileContent);

        // Находим кнопки для сохранения, чтения, удаления и добавления содержимого в файл
        Button saveButton = findViewById(R.id.saveButton);
        Button readButton = findViewById(R.id.readButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button putButton = findViewById(R.id.putButton);

        // Находим TextView для отображения содержимого файла
        TextView fileContentField = findViewById(R.id.fileContentField);

        // Устанавливаем обработчик нажатия для кнопки сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверяем, что поля имени файла и его содержимого не пустые
                if (fileName.getText().toString().matches("") || fileContent.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Какое-то из полей пустое", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем публичную директорию для документов во внешнем хранилище
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                // Создаем файл с указанным именем в директории
                File file = new File(storageDir, fileName.getText().toString());
                try {
                    // Если файла не существует, создаем новый
                    if (!file.exists()) {
                        boolean created = file.createNewFile();
                        if (created) {
                            FileWriter writer = new FileWriter(file);
                            writer.append(fileContent.getText().toString());
                            writer.flush();
                            writer.close();
                        }
                    } else {
                        // Если файл существует, перезаписываем его содержимое
                        FileWriter writer = new FileWriter(file);
                        writer.append(fileContent.getText().toString());
                        writer.flush();
                        writer.close();
                    }

                    Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Устанавливаем обработчик нажатия для кнопки чтения
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверяем, что поле имени файла не пустое
                if (fileName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Имя файла пустое", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем публичную директорию для документов во внешнем хранилище
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(storageDir, fileName.getText().toString());

                // Проверяем, что файл существует
                if (file.exists()) {
                    StringBuilder text = new StringBuilder();
                    try {
                        // Читаем содержимое файла
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String fileData = text.toString();

                    // Отображаем содержимое файла в TextView
                    fileContentField.setText(fileData);

                    Toast.makeText(getApplicationContext(), "Прочитано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Файла не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Устанавливаем обработчик нажатия для кнопки удаления
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверяем, что поле имени файла не пустое
                if (fileName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Имя файла пустое", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем публичную директорию для документов во внешнем хранилище
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(storageDir, fileName.getText().toString());

                // Проверяем, что файл существует
                if (file.exists()) {
                    // Создаем диалоговое окно для подтверждения удаления
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Вы точно хотите удалить файл?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Удаляем файл
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Toast.makeText(getApplicationContext(), "Файл удален", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Не удалось удалить файл", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Файл не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Устанавливаем обработчик нажатия для кнопки добавления содержимого в файл
        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверяем, что поля имени файла и его содержимого не пустые
                if (fileName.getText().toString().matches("") || fileContent.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Какое-то из полей пустое", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем публичную директорию для документов во внешнем хранилище
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                // Создаем файл с указанным именем в директории
                File file = new File(storageDir, fileName.getText().toString());
                try {
                    // Если файла не существует, выводим сообщение об ошибке
                    if (!file.exists()) {
                        Toast.makeText(getApplicationContext(), "Сначало создайте файл", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // Если файл существует, добавляем содержимое в конец файла
                        FileWriter writer = new FileWriter(file, true);
                        writer.append(fileContent.getText().toString());
                        writer.flush();
                        writer.close();
                    }

                    Toast.makeText(getApplicationContext(), "Изменено", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
