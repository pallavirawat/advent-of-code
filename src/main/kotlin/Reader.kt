object Reader {
    fun <T>read(fileName: String, readLineBlock: (inputline: String) -> T): List<T>{
        val acc = mutableListOf<T>()
        val filePath = "$fileName"
        this::class.java.classLoader.getResource(filePath)?.openStream()?.bufferedReader()?.forEachLine {
            acc.add(readLineBlock(it))
        }
        return acc
    }
}