/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.obligatorio2;

/**
 *
 * @author inazu
 */
public class Obligatorio2 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new ManejadorV1().setVisible(true);
        Procesador p;
        Proceso proceso;
        
        /*
        Un objeto Procesador, que contenga un objeto proceso y otros atributos
        para saber cuanto tiempo lleva ahí entro otros y sacarlo a x tiempo
        Por ende una lista de procesadores para poder ingresar los que quiero
        */
        /*
        Un objeto proceso, que tenga un atributo int de prioridad 1 a 99
        siendo 1 la más importante/prioritaria, tambien estaria bueno ver
        si es necesario una flag bool de estáBloqueado(o objeto bloqueado que tenga bloqueadoPorUsuario y bloqueadoPorES, para no tener que 
        eliminar o mover proceso HAY QUE PENSARLO
        
        Al insertar un proceso de SO o Usuario:
        -tiempo total que se ha ejecutado
        -Cada cuanto hacen una E/S
        -Tiempo que espera por E/S
        */
        
        
        
        /*
        ESTRUCTURAS:
        HASH
        para poder agregar, eliminar, buscar un proceso, se usa una función hash
        para beneficiarnos del O(1), por ende si quiero modificar algun flag o algo
        de un proceso, solo requiero una búsqueda O(1)
        TreeSet(AVL)
        Para poder mandar procesos de listo a ejecución, listos se representa como un
        TreeSet, para hacer un inorden y que recorra desde ahí los procesos de forma creciente en prioridad.
        (IMPORTANTE , la recorrida inorden se debe terminar siempre, se reccoren todos), por más que el que agregue
        sea el primero a ejecución.
        Esta ejecución en inorden debe ser a cada rato, teniendo en cuenta que al recorrer cada nodo conteniendo los procesos,
        en cada reocrrido inorden, me fijo si lleva mucho tiempo sin ejecutars y le aumento la prioridad, moviendoló a otro nodo
        más prioritario.
        HACER DE ALGUNA FORMA que este desplazamiento de lugar según prioridad, sea, si tiene por ejemplo prioridad 10 y pasa 1 seg,
        se cambia a prioridad nueve.
        En cada recorrida inorden, también genero una lista con cada proceso que se debe mover de prioridad por envejecimiento. Que 
        inmediatamente después, inserto esos procesos con su nueva prioridad en el TreeSet listos, O(logN) cada inserción-
        Capaz que eso último estaría bueno hacerlo async, y cada vez que se haga un inorden, cuando visito un proceso, me fijo
        y directamente lo muevo llamando a un método que lo mueva individualmente async, asi con cada inorden.
        
        Sino hacer todo el tema de mover por prioridades en un hilo aparte pero sincronizarlo debe ser MUY difícil.
        */
        /*
        SINO otra idea no tan mala es hacerlo todo en un HashTable con listas enlazadas, con lugares para 99 listas (prioridades).
        Pero hay que asegurarse que la función hash te mande cada proceso a donde corresponde siempre, para poder mantener algo orndenado
        pero es más difícil.
        Si se logra esto, todas las inserciones, eliminaciones, busquedas son de O(1), igualmente habría que recorrelo entero en orden. O(N)
        pero sería mejor que un TreeSet,
        */
    }
}
