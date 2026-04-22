package com.ciffelia.pdfpagesize.paper

data class Paper(val width: Int, val height: Int) {
    val sizeStandard: PaperSizeStandard? by lazy {
        findClosestStandard(width, height)
    }

    val orientation: Orientation by lazy {
        when {
            width > height -> Orientation.LANDSCAPE
            width < height -> Orientation.PORTRAIT
            else -> Orientation.SQUARE
        }
    }

    enum class Orientation {
        PORTRAIT, LANDSCAPE, SQUARE;
        override fun toString() = name.lowercase()
    }
}
