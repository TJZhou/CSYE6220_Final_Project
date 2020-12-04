export class ResponseWrapper <T> {
  code: number;
  message: string;
  data: T;

  public constructor(code: number, message: string, data: T) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
