/**
 * flexWidth
 * @param prop 每列的prop 可传''
 * @param tableData 表格数据
 * @param title 标题长内容短的，传标题  可不传
 * @param num 列中有标签等加的富余量
 * @returns 列的宽度
 * 注：prop,title有一个必传
 */
export const flexWidth = (prop, tableData, title, num = 0) => {
    if (tableData.length === 0) {//表格没数据不做处理
        return;
    }
    let flexWidth = 0;//初始化表格列宽
    let columnContent = '';//占位最宽的内容
    let canvas = document.createElement("canvas");
    let context = canvas.getContext("2d");
    context.font = "14px Microsoft YaHei";
    if ((prop === '') && title) {//标题长内容少的，取标题的值,
        columnContent = title
    } else {// 获取该列中占位最宽的内容
        let index = 0;
        for (let i = 0; i < tableData.length; i++) {
            const now_temp = tableData[i][prop] + '';
            const max_temp = tableData[index][prop] + '';
            const now_temp_w = context.measureText(now_temp).width
            const max_temp_w = context.measureText(max_temp).width
            if (now_temp_w > max_temp_w) {
                index = i;
            }
        }
        columnContent = tableData[index][prop]
        //比较占位最宽的值跟标题、标题为空的留出四个位置
        const column_w = context.measureText(columnContent).width
        const title_w = context.measureText(title).width
        if (column_w < title_w) {
            columnContent = title || '留四个字'
        }
    }
    // 计算最宽内容的列宽
    let width = context.measureText(columnContent);
    flexWidth = width.width + 40 + num
    return flexWidth + 'px';
}

export const formatDate=(row, column)=> {
    // 获取单元格数据
    let data = row[column.property]
    if(data == null) {
        return null
    }
    console.log(typeof data)
    if (data instanceof Date) {
        let dt = new Date(data)
        return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate()
    } else {
        return data;
    }
}
const padLeftZero = (str) => {
    return ('00' + str).substr(str.length)
}
//数据去重
export const unique = (arr) => {
    const res = new Map();
    return arr.filter((arr) => !res.has(arr.id) && res.set(arr.id, 1));
}