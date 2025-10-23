package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data //Sustituye getters y setters
@AllArgsConstructor //Sustituye a los constuctores
@NoArgsConstructor //Sustituye los argumentos vacíos
@ToString
public class Person {

    private String dni;
    private String name;
    private String surname;

    /*
    @NoArgsConstructor
     */

//    public Person(){
//    }

    /*
    @AllArgsConstructor
     */
//    public Person(String dni, String name, String surname) {
//        this.dni = dni;
//        this.name = name;
//        this.surname = surname;
//    }
//
     /*
     @Data
      */
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDni() {
//        return dni;
//    }
//
//    public void setDni(String dni) {
//        this.dni = dni;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }

    /*
    @ToString
     */
//
//    @Override
//    public String toString() {
//        return "Person{" +
//                "dni='" + dni + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                '}';
//    }
}
