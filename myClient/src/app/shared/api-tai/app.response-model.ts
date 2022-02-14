import { Element } from "./app.element-model";

export interface Response {
    id: Number,
    codeEnc: String,
    idEnc: Number,
    tipo:String,
    resp:Element[]
}