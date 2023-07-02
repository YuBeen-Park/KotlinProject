package com.android.kotlinproject.sort

fun main() {
    val test1 = arrayOf(
        "13-DE0201",
        "13-DE0401",
        "22-MO0815",
        "19-MO1299",
        "17-CO0901",
        "14-DE0511",
        "19-KE1102",
        "20-SP0404",
        "20-CO0794",
    )
    println(Solution().solution(test1).contentToString())
}

class Solution {
    /*
     MapNotNull ( Array -> List )
     transform 연산자를 적용해서 null이 아닌 값들로만 List를 구성해서 반환하는 함수
     */
    fun solution(assets: Array<String>): Array<String> =
        assets.mapNotNull { asset -> runCatching { asset.toAsset() }.getOrNull() }
            .sorted() // 리스트의 원본을 변경하지 않고 정렬된 리스트를 생성해서 리턴
            .filter(Asset::isValid) // 조건에 맞는 원소들만 리스트 : (T) -> Boolean
            .map(Asset::text) // 각 원소에 값을 변형해서 새로운 리스트 생성 : Asset::text = text값만 불러온다
            //.map{it.text}와 동일
            .distinct() // 중복된 원소 제거
            .toTypedArray() // list를 array로 변환
}

object ValidationError : Throwable()
/*
    object로 클래스를 정의하면 싱글턴 패턴 적용되서 객체가 한번만 생성(class와 차이점)
 */

fun String.toAsset(): Asset = when {
    length != 9 -> throw ValidationError
    slice(0..1).toIntOrNull() == null -> throw ValidationError
    get(2) != '-' -> throw ValidationError
    runCatching { Asset.Type.valueOf(slice(3..4)) }.isFailure -> throw ValidationError
    slice(5..6).toIntOrNull() == null -> throw ValidationError
    slice(7..8).toIntOrNull() == null -> throw ValidationError
    else -> Asset(
        text = this,
        yy = slice(0..1).toInt(),
        type = Asset.Type.valueOf(slice(3..4)),
        mm = slice(5..6).toInt(),
        no = slice(7..8).toInt(),
    )
}

data class Asset(
    val text: String,
    val yy: Int,
    val type: Type,
    val mm: Int,
    val no: Int,
) : Comparable<Asset> {
    /*
        Comparable<T> 인터페이스
        : compareTo() 메소드를 쉽게 사용할 수 있는 Comparable 인터페이스 제공
     */

    enum class Type { // enum class를 사용하면 자동으로 숫자로 바꿔줌 ( 0, 1 ,,)
        SP, KE, MO, CO, DE, // 우선순위 순서대로
    }
    /*
        Enum class
        - valueOf(String arg) : String값을 enum 에서 가져옴.  값이 없으면 예외 발생
        - valueOf(Class<T> class, String arg) : 넘겨받은 클래스에서 String을 찾아 enum에 가져옴
        - values() : enum 요소들을 순서대로 enum 타입의 배열로 리턴
        - name() : 호출된 값의 이름을 String으로 리턴
        - ordinal() : 해당값이 enum에 정의된 순서 리턴
        - compareTo() : enum에 지정된 순서를 비교
        - equals() : 지정된 객체가 enum정수와 같은 경우 true 리턴
     */

    // get()를 사용하면 필드값으로 생성하지 않고 isValid 호출했을 때 get함수 실행하는 방식 ( '=' 대입하는 것과 다름 )
    val isValid: Boolean
        get() {
            return (yy in 13..22) && (mm in 1..12) && when {
                yy == 13 && mm < 4 -> false
                yy == 22 && mm > 8 -> false
                else -> true
            } && no in 1..99
        }

    override fun compareTo(other: Asset): Int { //해당 객체리스트에 sorted 함수 사용했을 때 이 기준으로 정렬
        // 현재 값과 other 객체를 비교
        // 반환값이 양수 : 교체, 0/음수 : 정렬x
        return when {
            yy > other.yy -> 1
            yy < other.yy -> -1
            type.ordinal > other.type.ordinal -> 1
            type.ordinal < other.type.ordinal -> -1
            mm > other.mm -> 1
            mm < other.mm -> -1
            no > other.no -> 1
            no < other.no -> -1
            else -> 0
        }
    }
}
