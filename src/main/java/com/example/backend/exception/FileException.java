package com.example.backend.exception;

public class FileException extends  BaseException{

    public FileException (String code){
        super ("File ." +code);
    }

    public static FileException fileNull(){
        return new FileException("null");
    }

    public static FileException fileMaxSize(){
        return new FileException("max.size");
    }

    public static FileException unSupported(){
        return new FileException("unSupported.file.type");
    }
}
