import { BookAvailableInStore } from "./BookAvailableInStore";

export interface BookAvailability{
    libraryId: number,
    libraryName: string,
    storesAvailability: BookAvailableInStore[]
}