export const dateAddYear=(date,years)=> {
    let now = new Date(date);
    let intYear = now.getFullYear() + parseInt(years);
    let intMonth = now.getMonth () + 1; // 正常的月份，
    let intDay = now.getDate () - 1; // 日期 - 1
    if (intDay == 0) {
        intMonth--; // 减少一个月
        if (intMonth == 0) {
            intYear--; //0: 减少一年
            intMonth = 12;
            intDay = 31;
        }
        else if (intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) {
            intDay = 30; //4,6,9,11:30 天
        }
        else if (intMonth == 2) {
            intDay = 28; //2:28/29
            if (intYear % 4 == 0) {
                intDay = 29;
            }
        } else {
            intDay = 31; //1,3,5,7,8,10,12 :31 天
        }
    }

    let strMonth = (intMonth) < 10 ? "0" + (intMonth).toString() : (intMonth).toString();
    let strDay = (intDay) < 10 ? "0" + (intDay).toString() : (intDay).toString();
    let strEndDate = intYear + "-" + strMonth + "-" + strDay;
    return strEndDate;
}