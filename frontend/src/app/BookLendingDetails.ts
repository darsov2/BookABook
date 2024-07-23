import {BookLendingCopy} from "../BookLendingCopy";

export interface BookLendingDetails {
  id: number,
  name: string,
  isbn: string,
  imgUrl: string,
  authors:  string[],
  categories: string[],
  bookCopies: BookLendingCopy[]
}
