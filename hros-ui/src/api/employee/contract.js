import request from "@/request.js";

export function getEmpListByNearRenewal() {
    return request.get('/admin/employee/renewal/nearRenewalList');
}