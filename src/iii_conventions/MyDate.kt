package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.compareTo(other: MyDate): Int = when {
    year != other.year -> year - other.year
    month != other.month -> month - other.month
    else -> dayOfMonth - other.dayOfMonth
}

operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)

operator fun MyDate.plus(multipleTimeInterval: MultipleTimeInterval): MyDate =
    addTimeIntervals(multipleTimeInterval.interval, multipleTimeInterval.number)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class MultipleTimeInterval(val interval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = MultipleTimeInterval(this, number)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val result = current
                current = current.nextDay()
                return result
            }

        }
    }

}

operator fun DateRange.contains(d: MyDate): Boolean = d >= start && d <= endInclusive
