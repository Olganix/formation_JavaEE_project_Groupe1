export class Utils {


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
