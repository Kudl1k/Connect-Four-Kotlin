package connectfour



fun setupNames(): Array<String> {
    val names = arrayOf<String>("","")
    println("First player's name:")
    names[0] = readln()
    println("Second player's name:")
    names[1] = readln()
    return names
}

fun setupBoardSize(): IntArray{
    val expectedInput = Regex("\\s*\\d{1,2}\\s*[xX]\\s*\\d{1,2}(\\s*board)?\\s*")
    val numbers = IntArray(2)
    val passed: Boolean = false
    var counter: Int = 0
    cont@while (!passed){
        print("Set the board dimensions (Rows x Columns)\n" +
                "Press Enter for default (6 x 7)\n")
        val input = readln()
        counter = 0
        if (input.matches(expectedInput)){
            for (i in input.indices){
                if (input[i].isDigit()){
                    when (input[i].toString().toInt()) {
                        in 5..9 -> {
                            numbers[counter++] = input[i].toString().toInt()
                        } else -> {
                        if (counter == 0){
                            println("Board rows should be from 5 to 9")
                        } else if (counter == 1) {
                            println("Board columns should be from 5 to 9")
                        }
                        continue@cont
                    }
                    }
                    if (counter == 2) break@cont
                }
            }
        } else if (input == "") {
            numbers[0] = 6
            numbers[1] = 7
            break@cont
        } else {
            println("Invalid input")
        }
    }
    return numbers
}


fun generateGameField(rows: Int, cols: Int): MutableList<MutableList<Char>>{
    var gameField = mutableListOf(mutableListOf<Char>())
    for (i in 0 until cols-1){
        gameField.add(mutableListOf<Char>())
    }
    for (i in 0 until cols){
        for (j in 0 until rows){
            gameField[i].add(' ')
        }
    }
    return gameField
}

fun addSymbolToGameField(gameField: MutableList<MutableList<Char>> ,row: Int, char: Char): MutableList<MutableList<Char>>{
    for (i in gameField[row-1].size - 1 downTo 0){
        if (gameField[row-1][i] == ' '){
            gameField[row-1][i] = char
            break
        }
    }
    return gameField
}

fun checkIfCanBeAdded(gameField: MutableList<MutableList<Char>> ,row: Int): Boolean {
    var counter = 0
    for (i in gameField[row-1].size - 1 downTo  0){
        if (gameField[row-1][i] != ' '){
            counter++
        }
    }
    return counter != gameField[row-1].size
}

fun checkInput(input: String): Boolean {
    for (i in input.indices){
        if (input[i] !in ('0'..'9')){
            return false
        }
    }
    return true;
}



fun printBoard(nums: IntArray,gameField: MutableList<MutableList<Char>>){
    for (i in 1..nums[1]) {
        print(" $i")
    }
    println()

    for (row in 0 until nums[0]) {
        for (column in 0 until nums[1]) {
            print("${9553.toChar()}${gameField[column][row]}")
        }
        println(9553.toChar())
    }
    for (i in 0..nums[1]){
        when(i) {
            0 -> print("${9562.toChar()}${9552.toChar()}")
            nums[1] -> print("${9565.toChar()}")
            else -> print("${9577.toChar()}${9552.toChar()}")
        }
    }
    println()
}

fun checkGameState(nums: IntArray,gameField: MutableList<MutableList<Char>>): Int {
    // radky
    for (i in 0 until nums[1]){
        var Rocounter: Int = 0
        var Rxcounter: Int = 0
        for (j in 0 until nums[0]){
            when (gameField[i][j]) {
                'o' -> {
                    Rocounter++
                    Rxcounter = 0
                }
                '*' -> {
                    Rxcounter++
                    Rocounter = 0
                } else -> {
                Rxcounter = 0
                Rocounter = 0
                }
            }
            if (Rocounter == 4){
                return 1;
            } else if (Rxcounter == 4){
                return 2;
            }
        }
    }
    // sloupce
    for (i in 0 until nums[0]){
        var Cocounter: Int = 0
        var Cxcounter: Int = 0
        for (j in 0 until nums[1]){
            when (gameField[j][i]) {
                'o' -> {
                    Cocounter++
                    Cxcounter = 0
                }
                '*' -> {
                    Cxcounter++
                    Cocounter = 0
                } else -> {
                Cxcounter = 0
                Cocounter = 0
            }
            }
            if (Cocounter == 4){
                return 1
            } else if (Cxcounter == 4){
                return 2
            }
        }
    }
    //leva diagonala
    var a = 0
    while (a+3 != nums[1]){
        var b = 0
        while (b+3 != nums[0]){
            if (gameField[a][b] == 'o' && gameField[a+1][b+1] == 'o' && gameField[a+2][b+2] == 'o' && gameField[a+3][b+3] == 'o'){
                return 1
            }
            if (gameField[a][b] == '*' && gameField[a+1][b+1] == '*' && gameField[a+2][b+2] == '*' && gameField[a+3][b+3] == '*'){
                return 2
            }
            b++
        }
        a++
    }

    a = 0
    while (a+3 != nums[1]){
        var b = nums[0]-1
        while (b-3 != -1){
            if (gameField[a][b] == 'o' && gameField[a+1][b-1] == 'o' && gameField[a+2][b-2] == 'o' && gameField[a+3][b-3] == 'o'){
                return 1
            }
            if (gameField[a][b] == '*' && gameField[a+1][b-1] == '*' && gameField[a+2][b-2] == '*' && gameField[a+3][b-3] == '*'){
                return 2
            }
            b--
        }
        a++
    }

    for (i in 0 until nums[1]){
        for(j in 0 until nums[0]){
            if (gameField[i][j] == ' '){
                return 0;
            }
        }
    }
    return 3;
}


fun getGameCount(): Int {
    var result: String = ""

    while (true) {
        println("Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter\n" +
                "Input a number of games:")
        result = readln()
        if (checkInput(result)){
            if (result == "" || result.toInt() == 1){
                return 1
            } else if (result.toInt() > 1) {
                return result.toInt()
            } else {
                println("Invalid input")
            }
        } else {
            println("Invalid input")
        }
    }
}

fun printStartUpInfo(names: Array<String>, nums: IntArray, gamesCount: Int){
    println("${names[0]} VS ${names[1]}")
    println("${nums[0]} X ${nums[1]} board")
    if (gamesCount > 1){
        println("Total $gamesCount games")
    } else {
        println("Single game")
    }
}




fun main() {
    println("Connect Four")
    val names = setupNames()
    val numbers = setupBoardSize()
    var gamesCount = getGameCount()
    val numberOfRounds = gamesCount
    var round = 1
    var playerOneWins = 0
    var playerTwoWins = 0
    printStartUpInfo(names,numbers,gamesCount)
    var player = 1
    loop@while (gamesCount != 0){
        if (numberOfRounds > 1){
            println("Game #${round++}")
        }
        var gameField = generateGameField(numbers[0],numbers[1])
        var input: String = ""
        printBoard(numbers,gameField)
        while (input != "end"){
            when (player) {
                1 -> {
                    println("${names[0]}'s turn:")
                    input = readln()
                    player = 2
                    if (input == "end") break@loop
                    if (checkInput(input)){
                        when(input.toInt()){
                            in 1..numbers[1].toInt() -> {
                                if (checkIfCanBeAdded(gameField,input.toInt())){
                                    gameField = addSymbolToGameField(gameField,input.toInt(),'o');
                                    printBoard(numbers,gameField)
                                } else {
                                    player = 1
                                    println("Column $input is full")
                                }
                            } else -> {
                            println("The column number is out of range (1 - ${numbers[1]})")
                            player = 1;
                        }
                        }
                    } else {
                        player = 1;
                        println("Incorrect column number")
                    }
                }
                2 -> {
                    println("${names[1]}'s turn:")
                    input = readln()
                    player = 1
                    if (input == "end") continue
                    if (checkInput(input)){
                        when(input.toInt()){
                            in 1..numbers[1].toInt() -> {
                                if (checkIfCanBeAdded(gameField,input.toInt())){
                                    gameField = addSymbolToGameField(gameField,input.toInt(),'*');
                                    printBoard(numbers,gameField)
                                } else {
                                    player = 2
                                    println("Column $input is full")
                                }
                            } else -> {
                            println("The column number is out of range (1 - ${numbers[1]})")
                            player = 2;
                        }
                        }
                    } else {
                        player = 2;
                        println("Incorrect column number")
                    }
                }
            }
            when (checkGameState(numbers,gameField)){
                1 -> {
                    println("Player ${names[0]} won")
                    player = 2
                    playerOneWins += 2
                    break
                }
                2 -> {
                    println("Player ${names[1]} won")
                    player = 1
                    playerTwoWins += 2
                    break
                }
                3 -> {
                    println("It is a draw")
                    playerOneWins++
                    playerTwoWins++
                    break
                }
            }
        }
        if (numberOfRounds > 1){
            println("Score")
            println("${names[0]}: $playerOneWins ${names[1]}: $playerTwoWins")
        }
        gamesCount--
    }
    println("Game over!")
}
