export function generateMonth(monthArr: string[]): void {
  monthArr.push('All');
  const today = new Date();
    // add recent 24 months into list
  let aMonth = today.getMonth();
  let aYear = today.getFullYear();
  for (let i = 0; i < 24; i++) {
    // add 0 if month is (1 ~ 9), eg 2020-01, 2020-02...
    monthArr.push(aYear + (aMonth < 9 ? '-0' : '-') + (aMonth + 1));
    aMonth--;
    if (aMonth < 0) {
        aMonth = 11;
        aYear--;
    }
  }
}
