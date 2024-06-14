/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export class JsonUtils {
  static diff(obj1: any, obj2: any): any {
    const result: any = {};
    if (Object.is(obj1, obj2)) {
      return result;
    }
    if (!obj2 || typeof obj2 !== 'object') {
      return obj2;
    }
    Object.keys(obj1 || {}).concat(Object.keys(obj2 || {})).forEach((key) => {
      if (obj2[key] !== obj1[key] && !Object.is(obj1[key], obj2[key])) {
        result[key] = obj2[key];
      }
      if (typeof obj2[key] === 'object' && typeof obj1[key] === 'object') {
        const value = JsonUtils.diff(obj1[key], obj2[key]);
        if (value !== undefined) {
          result[key] = value;
        }
      }
    });
    return result;
  }

  static checkDiff(obj1: any, obj2: any): any {
    // const result: any = {};
    const result = obj1;
    if (Object.is(obj1, obj2)) {
      return result;
    }
    if (!obj2 || typeof obj2 !== 'object') {
      return obj2;
    }
    Object.keys(obj1 || {}).concat(Object.keys(obj2 || {})).forEach((key) => {
      if (obj2[key] !== obj1[key] && !Object.is(obj1[key], obj2[key])) {
        // result[key] = obj2[key];
        result[key].diff = true;
      }
      if (typeof obj2[key] === 'object' && typeof obj1[key] === 'object') {
        const value = JsonUtils.diff(obj1[key], obj2[key]);
        if (value !== undefined) {
          // result[key] = value;
          result[key].diff = true;
        }
      }
    });
    return result;
  }

  static findValue(obj: any, target: string): any{
    var result = obj;

    if(!target){
      console.error('Target `${obj}`.`${target}`');
      return null;
    }
    try{
      target.split(".").forEach(function(key){
        if(key == null || result == null){
          return;
        }
        result = result[key];
      });
      return result;
    }catch (e){
      console.error(`Cannot retrieve ${target} from ${JSON.stringify(obj)} - ${e}`);
    }
  }
}
