import {Book} from "./Book";

export interface Quantity {
  quantity: number;
  book: Book,
  status: string
}
