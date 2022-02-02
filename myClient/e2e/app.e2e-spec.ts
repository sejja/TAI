import { MyClientPage } from './app.po';

describe('my-client App', function() {
  let page: MyClientPage;

  beforeEach(() => {
    page = new MyClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
