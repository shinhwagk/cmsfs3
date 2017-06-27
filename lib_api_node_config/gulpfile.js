const gulp = require('gulp');
const ts = require('gulp-typescript');
const flatten = require('gulp-flatten');

gulp.task('default', function () {
  const tsProject = ts.createProject({
    declaration: true,
    sourceMap: false
  });

  const tsResult = gulp.src("src/**/*.ts")
    .pipe(ts(tsProject));

  return tsResult.dts
    .pipe(gulp.dest('./dist'));
});