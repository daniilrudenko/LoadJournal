package com.rudenko.models;

import org.omg.IOP.Encoding;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWorker {

    private File file;
    //----------------------------------------------------

    // Конструктор
    public FileWorker(String fileName){
        if(fileName.equals("")){
            System.out.println("Имя файла не указано.. По умолчанию присвоиться \"New1233219\"");
            fileName = "NewFile1233219";
        }
        file = new File(fileName);
    }
    //----------------------------------------------------

    // Метод создания файла

    public void createFile(){

        if(file.exists()) {
            System.out.println("Файл уже существует..");
            return;
        }

        try
        {
            boolean created = file.createNewFile();
            if(created)
                System.out.println("Файл - " + file.getName() + " создан.");
        }
        catch(IOException ex){
            System.out.println("Не удалось создать файл: ");
            ex.printStackTrace();
        }
    }
    //----------------------------------------------------

    // Метод проверяет существует ли файл
    public boolean doesFileExist(){

        return (file.exists());
    }
    //----------------------------------------------------

    // Запись в файл
    public void fileWrite(String...data){
        if(data.length == 0){
            System.out.println("Добавьте данные для записи в файл. ");
            return;
        }
        if(!doesFileExist()) {
            System.out.println("Сначала создайте файл. ");
            return;
        }
        if(file.canWrite()){
            try(FileWriter writer = new FileWriter(file, false))
            {
                for(String information : data){
                    writer.write(information);
                    writer.write("\n");
                }

                writer.flush();
            }
            catch(IOException ex){
                System.out.println("Неудалось записать данные: ");
                ex.printStackTrace();
            }
        }
        else System.out.println("Нельзя обратиться к файлу для записи..");
    }
    //----------------------------------------------------

    // Чтение из файла
    public List<String> fileRead() {

        List<String> list = null;

        if(!doesFileExist()) {
            System.out.println("Сначала создайте файл. ");
            return null;
        }
        if(file.canRead()){
            int counter = 0; // Счетчик для массива символов
            int symbol = 0;  // Вспомогательная переменная для считывания следующего символа из файла
            char [] data = new char[(int) file.length()]; // Массив символов
            try(FileReader reader = new FileReader(file))
            {
                while((symbol=reader.read())!=-1){ // Пока не конец файла
                    data[counter] = (char) symbol; // Добавить символ в массив
                    counter++;                     // Увеличить счетчик
                }
            }
            catch(IOException ex){
                System.out.println("Ошибка при чтении из файла: ");
                ex.printStackTrace();
                return null;
            }
            String words = String.valueOf(data); // Преобразование массива в строку

            // Разделение строки по символу "Перевод строки" и запись данных в коллекцию
            list = new ArrayList<>(Arrays.asList(words.split("\n")));
        }
        return list;
    }
    //----------------------------------------------------

    // Удаление файла
    public boolean deleteFile(){
        return file.delete();
    }
}
