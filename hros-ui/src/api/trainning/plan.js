import request from '@/request.js'

export const signUp = (id, employeeId) => {
    request.post('admin/training/plan/signUp/' + id, {
        params: {
            currentPage: currentPage.value,
            pageSize: pageSize.value,
            planName: planName.value,
            status: status.value
        }
    });
}