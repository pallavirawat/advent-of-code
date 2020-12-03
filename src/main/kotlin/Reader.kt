object Reader {
    fun <T>read(fileName: String, readLineBlock: (fileLine: String) -> T): List<T>{
        val acc = mutableListOf<T>()
        this::class.java.classLoader.getResource(fileName)?.openStream()?.bufferedReader()?.forEachLine {
            acc.add(readLineBlock(it))
        }
        return acc
    }
}