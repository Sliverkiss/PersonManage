import request from "@/request.js";

export function getTitles() {
    return request.get('/admin/assess/set/title/list/');
}
