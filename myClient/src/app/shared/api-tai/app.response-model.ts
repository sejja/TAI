import { Element } from "./app.element-model";

export interface TaiResponse {
    id: Number,
    codeEnc: String,
    idEnc: Number,
    resp:Element[]
}