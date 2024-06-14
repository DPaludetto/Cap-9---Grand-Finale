export class URLBuilder {
  url: string;

  params: string[] = [];

  private constructor(baseUrl: string) {
    this.url = baseUrl;
  }

  static new(url: string): URLBuilder {
    return new URLBuilder(url);
  }

  addUrlParam(paramName: string, paramValue?: string | number | boolean | unknown): URLBuilder {
    if (paramName && paramValue !== null && paramValue !== undefined && paramValue !== '') {
      this.params.push(`${paramName}=${paramValue}`);
    }
    return this;
  }

  addUrlParamArray(paramName: string, paramValue: string[] | number[]): URLBuilder {
    if (paramName && paramValue) {
      this.params.push(`${paramName}=${paramValue.join(',')}`);
    }
    return this;
  }

  build(): string {
    return this.params?.length ? this.url.concat('?').concat(this.params.join('&')) : this.url;
  }
}
