const gulp = require('gulp');
const ts = require('gulp-typescript');

const tsConfig = ts.createProject("tsconfig.json");

gulp.task('default', function () {
  return tsConfig.src()
    .pipe(tsConfig())
    .js.pipe(gulp.dest("lib"));
});