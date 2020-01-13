export class Utils {

  // ---------------------- Math
  public static mergeIntervals(a, b) {                // a : {start: x, end: y}

    if ( (a.start > b.end) ||
         (b.start > a.end) ) {              // case for detect distincts ranges.
      return [a, b];
    }

    const c = (a.start < b.start) ? a : b;
    const d = (a.start < b.start) ? b : a;

    if ( d.end < c.end ) {                    // d is included in c.
      return [c];
    }

    return [{start: c.start, end: d.end}];  // d extend c.
  }




  // ---------------------- Json
  public static clone(obj: object) {
    JSON.parse(JSON.stringify(obj));
  }


  // ---------------------- Time

  public static minutesToTimeDisplay_TimeZoneClient(value: number): string {    // value : minute from 00h00 UTC
    return Utils.minutesToTimeDisplay(value + (new Date()).getTimezoneOffset() );       // getTimezoneOffset() : diff minutes between UTC and client TimeZone
  }

  public static minutesToTimeDisplay(value: number): string {          // value : minute from 00h00
    let h = Math.floor(value / 60) % 24;              // modulo 24 because of possible  getTimezoneOffset(), we can be up to 24h , and under of 0h00
    if (h < 0) {
      h += 24;
    }
    const min = Math.floor(value - h * 60);
    return Utils.leftFill(String(h), 2, '0') + ':' + Utils.leftFill(String(min), 2, '0');
  }



  // ---------------------- String
  public static leftFill(str: string, nbChar: number, charToAdd: string) {
    while(str.length < nbChar) {
      str = charToAdd + str;
    }
    return str;
  }

  public static rightFill(str: string, nbChar: number, charToAdd: string) {
    while(str.length < nbChar) {
      str += charToAdd;
    }
    return str;
  }


}
