object Reader {
    fun <T>read(fileName: String, readLineBlock: (fileLine: String) -> T): List<T>{
        val acc = mutableListOf<T>()
        this::class.java.classLoader.getResource(fileName)?.openStream()?.bufferedReader()?.forEachLine {
            acc.add(readLineBlock(it))
        }
        return acc
    }

    fun <T>readAndGroup(fileName: String, groupBy: (fileLine: String) -> Boolean , readLineBlock: (fileLine: String) -> T): List<List<T>>{
        val group = mutableListOf<List<T>>()
        var acc = mutableListOf<T>()
        this::class.java.classLoader.getResource(fileName)?.openStream()?.bufferedReader()?.forEachLine {
            if (groupBy(it)){
                group.add(acc)
                acc = mutableListOf()
            }
            else{
                acc.add(readLineBlock(it))
            }
        }
        group.add(acc)
        return group
    }
}