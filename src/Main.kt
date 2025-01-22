import kotlin.math.roundToInt

/*
Ejercicio 4.3
Actualizar el ejercicio 4.2 para añadir a la clase Persona el siguiente comportamiento:

Debe retornar un saludo con su nombre... saludar():String
Debe retornar si la altura está por encima de la media (solo si altura >= 1.75)... alturaEncimaMedia():Boolean
Debe retornar si peso por encima de la media (solo si peso >= 70)... pesoEncimaMedia():Boolean
Sería conveniente añadir también un mét.odo obtenerDescImc() para usar en obtenerDesc(), que implemente el retorno de la descripción del rango de tipo de imc al que equivale su cálculo.
Nota: (Mejora: Enum class en https://www.baeldung.com/kotlin/enum)

Si el IMC es menos de 18.5, se encuentra dentro del rango de "peso insuficiente".
Si el IMC está entre 18.5 y 24.9, se encuentra dentro del rango de "peso saludable".
Si el IMC está entre 25.0 y 29.9, se encuentra dentro del rango de "sobrepeso".
Si el IMC es 30.0 o superior, se encuentra dentro del rango de "obesidad".

Debe implementar también un mét.odo que muestre una descripción completa de la persona... obtenerDesc():String. Por ejemplo, este mét.odo mostrará por pantalla algo así:

"Julia con una altura de 1.72m (Por debajo de la media) y un peso 64.7kg (Por encima de la media) tiene un IMC de 21,87 (peso saludable)".
Crear en el main() una estructura de datos con 4 o 5 personas más, recorrer la estructura y por cada persona debe saludar y mostrar su descripción completa.

Finalmente, revisa el IDE e intenta actualizar el modificador de visibilidad de los métodos de tu clase cómo os estará indicando...
veréis que los métodos que realmente no van a ser llamados desde fuera de la clase te recomienda que sean privados a la misma.
De esta manera estamos encapsulando estos métodos para que se puedan utilizar solo desde dentro de la clase y no sean públicos.
 */

fun redondearNumero2(valor: Double): Double {
    return (valor * 100).roundToInt() / 100.0
}
//No he encontrado un round justo, por lo que para redondear a 2 decimales he implementado una funcion global

class Persona(var peso: Double, var altura: Double) {
    var nombre: String? = ""
    val imc: Double
        get() = calcularImc()
    //En un getter no se pueden guardar los resultados para usarlos en un futuro
    //En este caso no es necesario poner el "set" privado, ya que cada vez que se consulte se actualizará "imc"

    constructor(nombre: String, peso: Double, altura: Double): this(peso, altura) { //this() llama al constructor primario
        //"imc" No está en el constructor, ya que es una propiedad calculada (se calcula automáticamente cada vez que se accede a ella)
        this.nombre = nombre
    }

    private fun calcularImc() = peso / (altura * altura)
    fun saludar(): String = "Me llamo $nombre"
    private fun alturaEncimaMedia(): Boolean = altura >= 1.75
    private fun pesoEncimaMedia():Boolean = peso >= 70
    private fun obtenerDescImc(): String {
        return when {
            imc <= 18.5 -> "peso insuficiente"
            imc in 18.5..24.9 -> "peso saludable"
            imc in 25.0..29.9 -> "sobrepeso"
            imc >=30.0 -> "obesidad"
            else -> "Datos Erróneos"
        }
    }

    fun obtenerDesc(): String = "$nombre con una altura de " +
            "$altura (${if (alturaEncimaMedia()) "Por encima de la media" else "Por debajo de la media"}) y un peso " +
            "$peso (${if (pesoEncimaMedia()) "Por encima de la media" else "Por debajo de la media"}) tiene un IMC de " +
            "${redondearNumero2(imc)} (${obtenerDescImc()})."

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Persona) return false

        return nombre == other.nombre && peso == other.peso && altura == other.altura
    }

    override fun hashCode(): Int {
        var result = nombre.hashCode()
        result += 31 * peso.hashCode()
        result += 31 * altura.hashCode()
        return result
    }

    override fun toString(): String {
        return "Nombre: $nombre, Peso: ${peso}kg, Altura: ${altura}m, Imc: ${redondearNumero2(imc)}kg/m2"
    }
}

fun main() {
    val persona = Persona(80.0, 1.86)
    val persona2 = Persona("Cati",50.0, 1.65)
    val persona3 = Persona("Aure", 75.5, 1.77)

    println(persona.toString())
    println(persona2.toString())
    println(persona3.toString())

    do {
        println("Es necesario un nombre: ")
        persona.nombre = readln().trim().lowercase().replaceFirstChar { it.uppercase() }
    } while (persona.nombre.isNullOrEmpty())

    println("Nombre: ${persona.nombre}, Peso: ${persona.peso}kg, Altura: ${persona.altura}m")

    println(persona.saludar())
    println(persona.obtenerDesc())

}