/** @param txt<br/>
 *  @param len : 생략시 기본값 20<br/>
 *  @param lastTxt : 생략시 기본값 "..."<br/>
 *  @returns 결과값
 * <br/>
 * <br/>
 * 특정 글자수가 넘어가면 넘어가는 글자는 자르고 마지막에 대체문자 처리<br/>
 *  ex) 가나다라마바사 -> textLengthOverCut('가나다라마바사', '5', '...') : 가나다라마...<br/>
 */
export const textLengthOverCut = (txt, len, lastTxt) => {
  if (txt == null) return;
  if (len == "" || len == null) {
    // 기본값
    len = 20;
  }
  if (lastTxt == "" || lastTxt == null) {
    // 기본값
    lastTxt = "...";
  }
  if (txt.length > len) {
    txt = txt.substr(0, len) + lastTxt;
  }
  return txt;
};
