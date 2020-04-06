package pl.pbednarz.randomcityapp.domain.usecases


interface InOutUseCase<in Input, out Output> {
    fun execute(input: Input): Output
}

interface InUseCase<in Input> {
    fun execute(input: Input)
}

interface OutUseCase<out Output> {
    fun execute(): Output
}

interface UseCase {
    fun execute()
}