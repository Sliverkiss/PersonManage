import request from "@/request.js";

export function addOrUpdateTransferItem(query) {
    return request.post('admin/department/transfer/approve', query);
}
