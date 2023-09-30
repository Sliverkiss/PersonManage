import request from "@/request.js";

export function listEmployeeColumnValues() {
    return request.get('/admin/employee/list/dasborad');
}

