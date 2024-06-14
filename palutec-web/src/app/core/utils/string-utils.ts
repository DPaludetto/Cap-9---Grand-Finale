export class StringUtils {

    public static isEmpty(value: string): boolean{
        return !value || value.length === 0;
    }
}