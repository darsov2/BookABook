export interface Book {
    isbn: string,
    name: string,
    description: string,
    imgUrl: string,
    publisher: string,
    id: number,
    categories: string[] | number[],
    authors: string[] | number[],
    averageRating: number,
    numPages: number,
    year: number,
}
